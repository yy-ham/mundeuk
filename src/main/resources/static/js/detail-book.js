document.addEventListener('DOMContentLoaded', () => {
    const bookDataString = sessionStorage.getItem('selectedBook');
    const book = JSON.parse(bookDataString);

    const thumbnail = book.thumbnail || 'https://via.placeholder.com/160x230?text=No+Image';
    const authors = book.authors?.join(', ') || '';
    const contents = book.contents || '제공된 책 소개가 없습니다.';
    const publisher = book.publisher || '정보 없음';
    const isbn = book.isbn || '정보 없음';
    const link = book.url || '#';


    if (!bookDataString) {
        alert('잘못된 접근입니다.');
        history.back();
        return;
    }

    // DOM 요소에 데이터 삽입
    document.getElementById('detail-title').innerText = book.title;
    document.getElementById('detail-cover').src = thumbnail;
    document.getElementById('detail-author').innerText = authors;
    document.getElementById('detail-contents').innerText = contents;
    document.getElementById('detail-publisher').innerText = publisher;
    document.getElementById('detail-isbn').innerText = isbn;
    document.getElementById('detail-link').href = link;
});

// 기록하기 버튼 클릭
document.getElementById('btnSave').addEventListener('click', () => {
    // 로그인 여부 확인
    const isSignedIn = localStorage.getItem('isSignedIn') === 'true';

    if (!isSignedIn) {
        alert('로그인이 필요한 기능입니다.');
        window.location.href = '../member/login.html';
    } else {
        window.location.href = '../review/review.html';
    }

});