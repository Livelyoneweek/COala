var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    console.log("connect 테스트")
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}


function connect2() {
    console.log("connect2 테스트")
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/queue/greetings2', function (greeting) {
            showGreeting2(JSON.parse(greeting.body).content);
        });
    });
}

function connect3() {
    console.log("connect3 테스트")
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/queue/greetings3', function (greeting) {
            showGreeting3(JSON.parse(greeting.body).content);
        });
    });
}



function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendName2() {
    stompClient.send("/app/hello2", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendName3() {
    stompClient.send("/app/hello3", {}, JSON.stringify({'name': $("#name").val()}));
}


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showGreeting2(message) {
    $("#greetings2").append("<tr><td>" + message + "</td></tr>");
}

function showGreeting3(message) {
    $("#greetings3").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#connect2" ).click(function() { connect2(); });
    $( "#connect3" ).click(function() { connect3(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#send2" ).click(function() { sendName2(); });
    $( "#send3" ).click(function() { sendName3(); });
});