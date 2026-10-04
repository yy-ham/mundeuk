document.getElementById('btnSignOut').addEventListener('click', () => {
    if (confirm("로그아웃 하시겠습니까?")) {
        // 로컬 스토리지에서 로그인 흔적 지우기
        localStorage.removeItem('isSignedIn');

        fetch('/api/v1/members/signout', {
            method: 'POST'
        }).then(response => {
            if(response.ok) {
                alert('로그아웃 되었습니다.');
                window.location.href = '../index.html';
            }
        }).catch(error => {
            console.error('로그아웃 에러:', error);
        });
    }
});