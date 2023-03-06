self.onload = function() {
    runRandomText('name');
    runRandomText('title');
    runRandomText('email');
    colorInButton(25, 75, [{r:255,g:255,b:255}, {r:3,g:255,b:3}, {r:255,g:255,b:255}], 3, 208, 3);
    determineImagePopin();
    logEvent('PAGE_LOAD')
    loadAnalytics("full");

    document.getElementById("resume-link").addEventListener('click', function() {logEvent("RESUME_DOWNLOAD");}, false);
    document.getElementById("source-code").addEventListener('click', function() {logEvent("VIEW_SOURCE");}, false);

}

document.getElementById("email").onclick = ev => {
    navigator.clipboard.writeText('jobs@brandoncunningham.dev');
    document.getElementById('myTooltip').innerHTML='Copied!';
}


document.getElementById("name").onmouseenter = ev => {
    if(textRandomMap.get('name').mouseEntered)
        return;
    textRandomMap.get('name').mouseEntered = true;

    runRandomText('name');
}

document.getElementById("name").onmouseleave = ev => {
    textRandomMap.get('name').mouseEntered = false;
}


document.getElementById("title").onmouseenter = ev => {
    if(textRandomMap.get('title').mouseEntered)
        return;
    textRandomMap.get('title').mouseEntered = true;
    runRandomText('title');
}
document.getElementById("title").onmouseleave = ev => {
    textRandomMap.get('title').mouseEntered = false;
}

const textRandomMap = new Map();
textRandomMap.set('name', {
    content: "Brandon Cunningham",
    running: false,
    delay: 25,
    mouseEntered: false
});
textRandomMap.set('title', {
    content: 'Software Developer',
    running: false,
    delay: 35
});
textRandomMap.set('email', {
    content: 'jobs@brandoncunningham.dev',
    running: false,
    delay: 20
});


const letters = "aBCDefghijklmnopqrStuvwxyz";
runRandomText = function(id) {
    if(textRandomMap.get(id).running)
        return;
    textRandomMap.get(id).running = true;


    for (var i = 0; i < (textRandomMap.get(id).content.length*3)+1; i++) {
        (function(i) {
            setTimeout(
                function() {
                    var nice = "<span style=\"color:white;\">";
                    var first = true;
                    for(var ii = 0; ii < textRandomMap.get(id).content.length; ii++) {
                        if(ii < i/3 || textRandomMap.get(id).content[ii]===' ')
                            nice+=textRandomMap.get(id).content[ii];
                        else {
                            if(first) {
                                nice+="</span><span style=\"color:lime\";>";
                                first = false;
                            }
                            nice += letters[Math.floor(Math.random() * 26)];
                        }
                    }
                    if(i>textRandomMap.get(id).content.length*3-5) {
                        textRandomMap.get(id).running = false;
                        nice = textRandomMap.get(id).content;
                    }
                    nice+="</span>";

                    document.getElementById(id).innerHTML = nice;
                    }, textRandomMap.get(id).delay * i);
        })(i);
    }
}

//3 208 3
//fancy button load in
colorInButton = function(duration, interval, startingColors, rTarget, gTarget, bTarget) {

    for(var i = 0; i < startingColors.length; i++) {
        startingColors[i].rChange = (rTarget-startingColors[i].r)/interval;
        startingColors[i].gChange = (gTarget-startingColors[i].g)/interval;
        startingColors[i].bChange = (bTarget-startingColors[i].b)/interval;
    }

    for (var i = 0; i < interval; i++) {
        (function(i) {
            setTimeout(
                function() {
                    let styling = "linear-gradient(to right";

                    for(var ii = 0; ii < startingColors.length; ii++) {
                        startingColors[ii].b += startingColors[ii].bChange;
                        startingColors[ii].r += startingColors[ii].rChange;
                        startingColors[ii].g += startingColors[ii].gChange;
                        let color = ", rgb("+startingColors[ii].r+","+startingColors[ii].g+","+startingColors[ii].b+")";
                        styling+=color;

                    }
                    styling+=")";
                    document.getElementById("resume-button").style.backgroundImage = styling;
                }, duration * i);
        })(i);
    }
}


let currentRotation = 180;
let rotationDirection = 1;
var breathingBackgroundInterval = setInterval(breathingBackground, 100);
function breathingBackground() {
    if(currentRotation > 335) {
        rotationDirection = -1;
    } else if(currentRotation < 25) {
        rotationDirection = 1;
    }

    currentRotation+=(1/1.5)*rotationDirection;
    document.getElementsByTagName('body')[0].style.backgroundImage="linear-gradient("+currentRotation+"deg, black, #1A1919FF)";
}



window.addEventListener('scroll', function (event) {
    determineImagePopin();
}, false);

window.onresize = function(event) {
    determineImagePopin();
};

function determineImagePopin() {
    var images = document.getElementsByClassName("skill-image");
    let counter = 0;
    for(var i = 0; i < images.length; i++) {
        if (isInViewport(document.getElementsByClassName("skill-image")[i])) {
            if(!document.getElementsByClassName("skill-image")[i].classList.contains("image-popin")) {
                counter+=1;
                (function(i, counter) {
                    setTimeout(
                        function() {
                            document.getElementsByClassName("skill-image")[i].classList.add("image-popin")
                        }, (counter*75));
                })(i, counter);
            }
        }
    }
}



function isInViewport(element) {
    // Get the bounding client rectangle position in the viewport
    var bounding = element.getBoundingClientRect();

    // Checking part. Here the code checks if it's *fully* visible
    // Edit this part if you just want a partial visibility
    if (
        bounding.top >= 0 &&
        bounding.left >= 0 &&
        bounding.right <= (window.innerWidth || document.documentElement.clientWidth) &&
        bounding.bottom <= (window.innerHeight || document.documentElement.clientHeight)
    ) {
        return true;
    } else {
        return false;
    }
}


function logEvent(event) {
    var http = new XMLHttpRequest();
    var url = "https://brandoncunningham.dev/api/analytics";

    http.open("POST", url, true);
    http.setRequestHeader("Content-Type", "application/json");
    http.setRequestHeader("sessionId", createOrGetSessionId());

    let requestBody = {
        type: event,
    };

    http.send(JSON.stringify(requestBody));
    http.onreadystatechange = (response) => {
        if (http.readyState === XMLHttpRequest.DONE) {
            loadAnalytics('lite');
        }
    }
}


function loadAnalytics(type) {
    var http = new XMLHttpRequest();
    var url = "https://brandoncunningham.dev/api/analytics?type="+type;

    http.open("GET", url, true);
    http.setRequestHeader("sessionId", createOrGetSessionId());
    http.send();

    http.onreadystatechange = (response) => {
        if (http.readyState === XMLHttpRequest.DONE) {
            if(http.status === 200) {
                var response = JSON.parse(http.response);

                document.getElementById("liveUsers").innerText=response.liveUsers;
                document.getElementById("totalPageLoads").innerText=response.totalPageLoads;
                document.getElementById("totalResumeDownloads").innerText=response.resumeDownloads;

                if(type === 'full') {
                    barColors = [];
                    for (let i = 0; i < response.chartLabels.length; i++) {
                        barColors.push("#01ab01");
                    }


                    dailyVisitsChart.data.labels = response.chartLabels;
                    dailyVisitsChart.data.datasets[0].data = response.chartValues;
                    dailyVisitsChart.data.datasets[0].backgroundColor = barColors
                    dailyVisitsChart.update();
                }


            } else {
                document.getElementById("loading-icon").style.display = 'none';
                document.getElementById("loading-failed").style.display = null;
            }
        }
    }
}

function createOrGetSessionId() {
    if(localStorage.getItem("sessionId") === null) {
        let sessionId = createUUID();
        localStorage.setItem("sessionId", sessionId);
    }
    return localStorage.getItem("sessionId");
}
function createUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}


var dailyVisitsChart = new Chart("myChart", {
    type: "bar",
    data: {
        labels: [],
        datasets: [{
            backgroundColor: [],
            data: []
        }]
    },
    options: {
        legend: {display: false},
        title: {
            display: true,
            text: "Daily page loads",
            fontColor: "white",
            fontSize: 20
        },
        scales: {
            yAxes: [{
                ticks: {
                    fontColor: "white",
                    fontSize: 20,
                }
            }],
            xAxes: [{
                ticks: {
                    fontColor: "white",
                    fontSize: 20,
                }
            }]
        }
    }
});









