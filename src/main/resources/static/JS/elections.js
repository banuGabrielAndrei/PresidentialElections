let intervalId;

function countdownElections() {
    let startElections = new Date("Apr 26, 2024 20:30:00").getTime();
    let today = new Date().getTime();
    let remainingTime = startElections - today;
    let days = Math.floor(remainingTime / (1000 * 60 * 60 * 24));
    let hours = Math.floor((remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);
    document.getElementById("countDown").innerHTML ="Elections begin in " + days + "days " + hours + "hours " + minutes + "minutes " + seconds + "seconds";
    let voteButtons = document.getElementsByClassName("voteButton");
    for (let button of voteButtons) {
        button.disabled = true;
    }
    if (remainingTime <= 0) {
        document.getElementById("countDown").innerHTML = "ELECTIONS STARTED!"
        for (let button of voteButtons) {
            button.disabled = false;
        }
        clearInterval(intervalId);
    }   
}
countdownElections();
intervalId = setInterval(countdownElections, 1000);