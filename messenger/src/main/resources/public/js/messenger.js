// get messages
function poll() {
    setTimeout( function() {
        $.ajax({
            url: 'msg/read',
            type: 'GET',
            success: function(data) {
                for (var i = 0; i < data.length; i++) {
                    var msg = data[i];
                    var msg_time = msg.time.replace("T", " ").split(".")[0]
                    $( "#msg-table > tbody" ).append("<tr><td>" + msg_time + "</td><td>" + msg.sender + "</td><td>" + msg.text + "</td></tr>");
                }
                poll();  //call poll() function again after successfully calling the first time.
            }
        });
    }, 3000);
}

$( document ).ready(function() {
    poll();
});
