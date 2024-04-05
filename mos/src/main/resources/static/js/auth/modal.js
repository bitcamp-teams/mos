document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById('loginModal');
    const btnOpenModal = document.querySelector('.btn-open-modal');
    const loginModalContent = document.getElementById('loginModalContent');

    // 로그인 버튼 클릭 시 모달 열기
    btnOpenModal.addEventListener("click", async () => {
      try {
        // login.html 가져오기
        const response = await fetch('/auth/login');
        const htmlContent = await response.text();
        // 모달 내용에 login.html 삽입
        loginModalContent.innerHTML = htmlContent;
        // 모달 열기
        modal.style.display = "flex";

        // 모달 외부 클릭 시 모달 닫기
      window.addEventListener("click", (event) => {
        if (event.target === modal) {
          modal.style.display = "none";
        }
      });
      } catch (error) {
        console.error("페이지를 가져오는 중 에러 발생:", error);
      }
    });
  });