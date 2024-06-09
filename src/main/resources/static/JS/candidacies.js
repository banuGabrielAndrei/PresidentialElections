let intervalId;
let candidacyDeadlineElement = document.getElementById("startElectionProcess");
let candidacyDeadline = candidacyDeadlineElement.textContent;
let buttonText = document.getElementById("candidatesButtonText");

function remainingTime() {
    let today = new Date().getTime();
    let deadline = Date.parse(candidacyDeadline);
    let remainingTime = deadline - today;
    let days = Math.floor(remainingTime / (1000 * 60 * 60 * 24));
    let hours = Math.floor((remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);
    document.getElementById("remainingTime").innerHTML ="Candidacy ends in " + days + "days " + hours + "hours " + minutes + "minutes " + seconds + "seconds";
    if (remainingTime <= 0) {
        document.getElementById("remainingTime").innerHTML = "ELECTIONS STARTED!";
        document.getElementById("submitCandidate").removeAttribute("href");
        document.getElementById("submitCandidate").classList.add("disabled");
        document.getElementById("submitCard").classList.add("bg-danger");
        buttonText.textContent = "Vote Here!";
        clearInterval(intervalId);
    }
}

remainingTime();
intervalId = setInterval(remainingTime, 1000);