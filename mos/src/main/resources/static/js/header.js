window.addEventListener('scroll', function () {
  const header = document.getElementById('header');
  const scrollPosition = window.pageYOffset
      || document.documentElement.scrollTop;

  if (scrollPosition > 0) {
    header.classList.add('hidden');
  } else {
    header.classList.remove('hidden');
  }
});