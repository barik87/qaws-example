// save file
$( "#btn-save-file" ).click(function() {
    $.ajax({
        url: 'file/save',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ "text": $( "#text-to-save" ).val() }),
        success: function(data){
            $( "#save-confirm" ).addClass("text-success");
            $( "#save-confirm" ).val(data);
        },
        error: function(data){
            $( "#save-confirm" ).addClass("text-danger");
            $( "#save-confirm" ).val(data.message);
        }
    });
});

$( "#text-to-save" ).keypress(function() {
    $( "#save-confirm" ).removeClass("text-success text-danger");
    $( "#save-confirm" ).val("");
});

// read file
$( "#btn-read-file" ).click(function() {
    $.ajax({
        url: 'file/read',
        type: 'GET',
        success: function(data){
            $( "#read-confirm" ).val(data);
        },
        error: function(data){
            $( "#read-confirm" ).addClass("text-danger");
            $( "#read-confirm" ).val(data.message);
        }
    });
});
