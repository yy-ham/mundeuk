document.addEventListener("DOMContentLoaded", function() {
    // 1. 로그인 상태 UI 변경
    if (localStorage.getItem('isSignedIn') === 'true') {
        document.querySelector('#profile-text').innerText = '마이페이지';
        document.querySelector('#profile-link').href = '/member/mypage.html';
    }

    // 2. 검색 화면 초기 접속 시 검색창만 보이고 탭과 결과 영역은 숨김
    tabsContainer.style.display = 'none';
    contentContainer.style.display = 'none';
});

// 상태 관리 변수
let currentTab = 'tab-all';
let currentKeyword = '';
let currentPage = 1;
let booksData = []; // 검색 결과 누적 배열
let isEnd = false;  // 카카오 API 더 이상 결과가 없는지 여부

// DOM 요소
const searchBtn = document.querySelector('.search-btn');
const searchInput = document.getElementById('keyword');
const tabsContainer = document.querySelector('.search-tabs');
const contentContainer = document.querySelector('.search-content');
const bookList = document.getElementById('book-list');
const headerMoreBtn = document.getElementById('header-more-btn');
const bottomLoadMore = document.getElementById('bottom-load-more');

searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        executeSearch();
    }
});

searchBtn.addEventListener('click', () => {
    executeSearch();
});



// --- 검색 실행 공통 함수 ---
function executeSearch() {
    const keyword = searchInput.value.trim();

    if (!keyword) {
        alert("검색어를 입력해주세요.");
        return;
    }

    // 2. 기존 검색 데이터 및 상태 초기화
    currentKeyword = keyword;
    currentPage = 1;
    booksData = [];

    // 3. 검색이 시작되면 탭과 결과 영역을 화면에 표시
    tabsContainer.style.display = 'flex';
    contentContainer.style.display = 'block';

    fetchBooks(); // API 호출
}

// API 통신 요청
async function fetchBooks() {
    try {
        // 컨트롤러 호출 (검색어와 현재 페이지 번호 전달)
        const response = await fetch(`/api/v1/books?keyword=${currentKeyword}&page=${currentPage}`);
        if (!response.ok) {
            throw new Error("네트워크 응답 에러");
        }

        const data = await response.json();

        // API 응답 데이터를 기존 배열에 누적
        booksData = booksData.concat(data.documents);
        isEnd = data.meta.is_end; // 다음 페이지가 존재하는지 여부

        renderList();
    } catch (error) {
        console.error("검색 중 오류 발생:", error);
    }
}

// 화면 렌더링
function renderList() {
    renderBookList(); // 책 검색 결과
}


// 책 검색 결과 렌더링
function renderBookList() {
    const bookSection = document.getElementById('book-section'); //

    // 선택된 탭에 따라 책 섹션 전체의 노출 여부 결정
    if (currentTab === 'tab-all' || currentTab === 'tab-book') {
        bookSection.style.display = 'block'; // 통합, 책 탭에서는 보임
    } else {
        bookSection.style.display = 'none';  // 영화, TV 탭에서는 숨김
        return; // 어차피 숨겼으므로 아래 리스트를 그리는 로직은 실행할 필요 없음
    }

    bookList.innerHTML = '';

    // 통합 탭일 경우 앞에서부터 3개만 자르고, 책 탭일 경우 누적된 전체 데이터 사용
    let displayData = currentTab === 'tab-all' ? booksData.slice(0, 3) : booksData;

    // 결과가 없을 때의 처리
    if (displayData.length === 0) {
        bookList.innerHTML = '<li style="text-align:center; padding: 40px 0;">검색 결과가 없습니다.</li>';
        headerMoreBtn.style.display = 'none';
        bottomLoadMore.style.display = 'none';
        return;
    }

    // 리스트 DOM 생성 및 조립
    displayData.forEach(item => {
        // API 응답 데이터 가공 (연도 추출, 저자 배열 문자열화, 표지 이미지 대체)
        const year = item.datetime.substring(0, 4);

        const authors = item.authors && item.authors.length > 0 ? item.authors.join(', ') : '';
        const thumbnail = item.thumbnail || 'https://via.placeholder.com/120x170?text=No+Image';

        const li = document.createElement('li');
        li.className = 'result-item';
        li.innerHTML = `
            <div class="poster">
                <img src="${thumbnail}" alt="${item.title} 표지" style="width:100%; height:100%; object-fit:cover;">
            </div>
            <div class="info">
                <h3 class="title">${item.title}</h3>
                <span class="meta">${authors}</span>
                <span class="meta">${item.publisher} · ${year}</span>
            </div>
        `;
        bookList.appendChild(li);
    });

    // 탭에 따른 버튼 노출 제어
    if (currentTab === 'tab-all') {
        // [통합 탭] 검색 결과가 3개 이상이면 상단 더보기 노출, 하단 더보기는 숨김
        headerMoreBtn.style.display = booksData.length > 3 ? 'block' : 'none';
        bottomLoadMore.style.display = 'none';
    } else {
        // [개별 탭] 상단 더보기 숨김, 하단 더보기는 isEnd 여부에 따라 결정
        headerMoreBtn.style.display = 'none';
        bottomLoadMore.style.display = isEnd ? 'none' : 'flex';
    }
}

// 탭 클릭
document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', (e) => {
        // 탭 활성화 UI 변경
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        e.target.classList.add('active');

        // 상태 업데이트 후 화면 다시 그리기
        currentTab = e.target.dataset.target;
        renderList();
    });
});

// 상단 우측 '더보기' 클릭 시 탭 이동 (통합 -> 개별 탭)
headerMoreBtn.addEventListener('click', () => {
    const bookTab = document.querySelector('[data-target="tab-book"]');
    // 책 탭 클릭 이벤트 발생시킴
    if (bookTab) {
        bookTab.click();
    }
});

// 하단 '더보기' 버튼 클릭 시 다음 페이지 10개 추가 호출
document.querySelector('.load-more-btn').addEventListener('click', () => {
    currentPage++; // 페이지 1 증가
    fetchBooks();  // API 재호출 (booksData 배열에 10개가 누적됨)
});