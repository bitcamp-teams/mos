$(".resizable-column").resizable({
  handles: "e", //East 방향에만 핸들 달기
  handleClass: "custom-handle",
  stop: function (event, ui) {
    $(this).animate({opacity: 0.5}, 300)
    .animate({opacity: 1}, 300); // 깜박이는 효과 애니메이션
  }
});