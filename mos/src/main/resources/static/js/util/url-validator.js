function extractImageUrl(text) {
  // text가 null이나 undefined인 경우 null 반환
  if (!text) {
    return null;
  }

  const regex = /!\[(.*?)]\((.*?)\)/;
  const match = text.match(regex);

  if (match && match.length > 2 && isValidImageUrl(match[2])) {
    return match[2];
  }

  return null;
}

function isValidImageUrl(url) {
  return new Promise((resolve) => {
    // URL이 비어있거나 null인 경우 false 반환
    if (!url) {
      resolve(false);
      return;
    }

    try {
      // URL을 생성하고, 프로토콜이 http나 https인지 확인
      const parsedUrl = new URL(url);
      if (!['http:', 'https:'].includes(parsedUrl.protocol)) {
        resolve(false);
        return;
      }

      // 이미지 확장자 검사
      const validExtensions = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'];
      const extension = parsedUrl.pathname.split('.').pop().toLowerCase();
      if (!validExtensions.includes(extension)) {
        resolve(false);
        return;
      }

      // Image 객체를 사용하여 이미지 로드 및 렌더링 가능 여부 확인
      const img = new Image();
      img.onload = () => resolve(true);
      img.onerror = () => resolve(false);
      img.src = url;
    } catch (error) {
      // URL 형식이 잘못된 경우 false 반환
      resolve(false);
    }
  });
}