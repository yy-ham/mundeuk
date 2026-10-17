// 전역변수
let contentData = null;

// 페이지 로딩 시 데이터 바인딩 및 기본값 세팅 로직
document.addEventListener('DOMContentLoaded', () => {

    // 1. 오늘 날짜(sysdate) 구해서 YYYY-MM-DD 포맷으로 변환
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const sysdate = `${year}-${month}-${day}`;

    // 2. 시작일과 종료일에 오늘 날짜를 기본값으로 세팅
    document.getElementById('startDate').value = sysdate;
    document.getElementById('endDate').value = sysdate;

    // 3. sessionStorage에서 데이터 꺼내기
    const contentType = sessionStorage.getItem('contentType');
    const savedData = sessionStorage.getItem('selectedBook');

    if (savedData) {
        contentData = JSON.parse(savedData);
        renderContent(contentType, contentData);
    } else {
        document.querySelector('.content-title').textContent = '선택된 콘텐츠가 없습니다.';
    }
});

// 리뷰 저장
document.getElementById('btnSave').addEventListener('click', () => {
    // 1. 입력값 가져오기
    const contentType = ContentType.BOOK;
    const ratingValue = document.getElementById('ratingValue').textContent.trim();
    const startDate = document.getElementById('startDate').value.trim();
    const endDate = document.getElementById('endDate').value.trim();
    const reviewText = document.getElementById('reviewText').value.trim();

    // 2.필수입력 값 검증
    if (!startDate || !endDate || !reviewText) {
        alert('필수 항목을 모두 입력해주세요.');
        return;
    }

    // 3. 전송할 데이터 객체 생성 (백엔드 Request DTO 필드명과 일치해야 함)
    let isbn10 = '';
    let isbn13 = '';
    if (contentData.isbn) {
        const isbnArr = contentData.isbn.split(" ");
        for (let i = 0; i < isbnArr.length; i++) {
            if (isbnArr[i].length == 10) {
                isbn10 = isbnArr[i];
            } else if (isbnArr[i].length == 13) {
                isbn13 = isbnArr[i];
            }
        }
    }

    const requestData = {
        reviewRequestDto: {
            contentType: contentType,
            startDate: startDate,
            endDate: endDate,
            rating: ratingValue,
            content: reviewText
        },
        bookRequestDto: {
            title: contentData.title,
            author: contentData.authors?.join(', ') || '',
            publisher: contentData.publisher,
            description: contentData.contents,
            publishDate: contentData.datetime.substring(0, 10),
            isbn10: isbn10,
            isbn13: isbn13,
            image: contentData.thumbnail
        }
    };

    // 4. POST 요청 전송
    fetch('/api/v1/reviews', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (response.ok) {
                alert("리뷰가 저장되었습니다.");
                window.location.href = '../index.html';

                // 성공 시 리뷰 상세 화면으로 이동
                // window.location.href = '../index.html';
            } else {
                response.text().then(errorMessage => {
                    alert("저장 중 오류가 발생했습니다.")
                    console.log('실패: ' + errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와 통신 중 오류가 발생했습니다.');
        });
});

function renderContent(contentType, data) {
    if (contentType == ContentType.BOOK) {
        renderBook(data);
    }
}

function renderBook(data) {
    const year = data.datetime.substring(0, 4);
    document.querySelector('.content-title').innerText = data.title;
    document.querySelector('.content-author').innerText = data.authors.join(', ');
    document.querySelector('.content-image').src = data.thumbnail;
    document.querySelector('.content-info-detail').innerText = `${data.publisher} · ${year}`;
}

// 글자 수 계산
const reviewText = document.getElementById('reviewText');
const charCount = document.getElementById('charCount');

reviewText.addEventListener('input', function() {
    // 현재 입력된 글자 수 천 단위 콤마 포맷팅
    charCount.textContent = this.value.length.toLocaleString();
});


// 별점 스와이프
const starsContainer = document.getElementById('starsContainer');
const ratingValueDisplay = document.getElementById('ratingValue');
const stars = document.querySelectorAll('.star .fill');

// 별점 계산 함수 (0.5 단위)
function calculateRating(e) {
    const rect = starsContainer.getBoundingClientRect();
    const clientX = e.touches ? e.touches[0].clientX : e.clientX;

    let x = clientX - rect.left;
    const width = rect.width;

    x = Math.max(0, Math.min(x, width));

    let rawRating = (x / width) * 5;
    let rating = Math.ceil(rawRating * 2) / 2;

    updateStars(rating);
}

// UI에 별점 색칠하기
function updateStars(rating) {
    ratingValueDisplay.textContent = rating.toFixed(1);

    stars.forEach((starFill, index) => {
        const starValue = index + 1;
        if (rating >= starValue) {
            starFill.style.width = '100%';
        } else if (rating === starValue - 0.5) {
            starFill.style.width = '50%';
        } else {
            starFill.style.width = '0%';
        }
    });
}

    // 마우스 드래그 및 터치 스와이프 이벤트 리스너 등록
    let isDragging = false;

    starsContainer.addEventListener('mousedown', (e) => { isDragging = true; calculateRating(e); });
    document.addEventListener('mousemove', (e) => { if (isDragging) calculateRating(e); });
    document.addEventListener('mouseup', () => { isDragging = false; });

    starsContainer.addEventListener('touchstart', (e) => { calculateRating(e); }, {passive: false});
    starsContainer.addEventListener('touchmove', (e) => {
        e.preventDefault();
        calculateRating(e);
    }, {passive: false});

document.querySelector('.btn-cancel').addEventListener('click', () => {
    const isCancel = confirm('작성을 취소하시겠습니까? 지금까지 작성된 내용은 저장되지 않습니다.');
    if (isCancel) {
        history.back();
    }
});