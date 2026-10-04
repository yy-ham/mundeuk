document.getElementById('btnSignUp').addEventListener('click', () => {
    // 1. 입력값 가져오기
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const nickname = document.getElementById('nickname').value.trim();

    // 2. 필수입력 값 검증
    if (!email || !password || !nickname) {
        alert('필수 항목을 모두 입력해주세요.');
        return;
    }

    // 3. 전송할 데이터 객체 생성 (백엔드 Request DTO 필드명과 일치해야 함)
    const requestData = {
        email: email,
        password: password,
        nickname: nickname
    };

    // 4. POST 요청 전송
    fetch('/api/v1/members', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (response.ok) {
                alert('회원가입이 완료되었습니다.');
                // 성공 시 로그인 화면으로 이동
                window.location.href = 'signin-email.html';
            } else {
                // 백엔드에서 넘겨준 에러 메시지 출력 (예: 중복 이메일)
                response.text().then(errorMessage => {
                    alert('회원가입 실패: ' + errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와 통신 중 오류가 발생했습니다.');
        });
});