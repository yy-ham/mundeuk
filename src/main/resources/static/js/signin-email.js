document.getElementById('btnSignIn').addEventListener('click', () => {
    // 1. 입력값 가져오기
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    // 2.필수입력 값 검증
    if (!email || !password) {
        alert('필수 항목을 모두 입력해주세요.');
        return;
    }

    // 3. 전송할 데이터 객체 생성 (백엔드 Request DTO 필드명과 일치해야 함)
    const requestData = {
        email: email,
        password: password,
    };

    // 4. POST 요청 전송
    fetch('/api/v1/members/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (response.ok) {
                localStorage.setItem('isSignedIn', 'true');

                // 성공 시 홈 화면으로 이동
                window.location.href = '../index.html';
            } else {
                response.text().then(errorMessage => {
                    alert('로그인 실패: ' + errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와 통신 중 오류가 발생했습니다.');
        });
});