let intervalId;

function remainingTime() {
    let deadline = new Date("Apr 22, 2024 01:43:00").getTime();
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
        clearInterval(intervalId);
    }
}

remainingTime();
intervalId = setInterval(remainingTime, 1000);