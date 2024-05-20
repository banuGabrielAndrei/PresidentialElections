let intervalId;
let startElectionsElement = document.getElementById("startElections");
let startElections = startElectionsElement.textContent;

let votingIntervalId;
let startVoting = false;
let endVotingElement = document.getElementById("endVoting");
let endVoting = endVotingElement.textContent;

function countdownStartElections() {
    let voteTime = Date.parse(startElections);
    let today = new Date().getTime();
    let remainingTime = voteTime - today;
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
        for (let button of voteButtons) {
            button.disabled = false;
        }
        startVoting = true;
        clearInterval(intervalId);
        
    }   
}

function endVotingTimer() {
    if (startVoting) {
        let endVoteTime = Date.parse(endVoting);
        let today = new Date().getTime();
        let remainingTime = endVoteTime - today;
        let days = Math.floor(remainingTime / (1000 * 60 * 60 * 24));
        let hours = Math.floor((remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);
        document.getElementById("countDown").innerHTML ="<span class='started'>ELECTIONS STARTED!</span><br>Elections ends in " + days + "days " + hours + "hours " + minutes + "minutes " + seconds + "seconds";
        let voteButtons = document.getElementsByClassName("voteButton");
        if (remainingTime <= 0) {
            document.getElementById("countDown").innerHTML = "ELECTIONS ENDED! CHECK THE RANKING."
            for (let button of voteButtons) {
                button.disabled = true;
            }
            clearInterval(votingIntervalId);
        }
    }
}

countdownStartElections();
intervalId = setInterval(countdownStartElections, 1000);

endVotingTimer();
votingIntervalId = setInterval(endVotingTimer, 1000);
