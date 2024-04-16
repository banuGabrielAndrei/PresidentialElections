let countdownInterval;

function remainingTime() {
    let deadline = new Date("Apr 17, 2024 23:50:00").getTime();
    let today = new Date().getTime();
    let remainingTime = deadline - today;
    let days = Math.floor(remainingTime / (1000 * 60 * 60 * 24));
    let hours = Math.floor((remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);
    document.getElementById("remainingTime").innerHTML ="Elections begin in " + days + "days " + hours + "hours " + minutes + "minutes " + seconds + "seconds";
    if (remainingTime <= 0) {
        document.getElementById("remainingTime").innerHTML = "ELECTIONS STARTED!";
        document.getElementById("submitCandidate").removeAttribute("href");
        document.getElementById("submitCandidate").classList.add("disabled");
        document.getElementById("submitCard").classList.add("bg-danger");
        countdownElections();
        countdownInterval = setInterval(countdownElections, 1000);
        clearInterval(intervalId);
    }
}

function countdownElections() {
    let endElectionsDate = new Date("Apr 18, 2024 12:55:00").getTime();
    let currentDate = new Date().getTime();
    let timeToVote = endElectionsDate - currentDate;
    let days = Math.floor(timeToVote / (1000 * 60 * 60 * 24));
    let hours = Math.floor((timeToVote % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((timeToVote % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((timeToVote % (1000 * 60)) / 1000);
    document.getElementById("countDown").innerHTML = "Elections end in: " + days + "days " + hours + "hours " + minutes + "minutes " + seconds + "seconds ";
    if (timeToVote <= 0) {
        document.getElementById("countDown").innerHTML = "ELECTIONS ENDED!";
        document.getElementById("candidatesCard").classList.add("disabled");
        document.getElementById("candidatesCard").classList.add("bg-danger");
        clearInterval(countdownInterval);
    }
}

remainingTime();
let intervalId = setInterval(remainingTime, 1000);