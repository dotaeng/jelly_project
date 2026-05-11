
function toggleInterest(button) {
  if (button.classList.contains("saved")) {
    button.classList.remove("saved");
    button.textContent = "관심 저장";
  } else {
    button.classList.add("saved");
    button.textContent = "관심 저장";
  }
}
