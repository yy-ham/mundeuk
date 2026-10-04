document.addEventListener("DOMContentLoaded", function() {
    // 로그인 된 상태라면 로그인 아이콘 -> 마이페이지로 변경
    if (localStorage.getItem('isSignedIn') === 'true') {
        document.querySelector('#profile-text').innerText = '마이페이지';
        document.querySelector('#profile-link').href = '/member/mypage.html';
    }
});