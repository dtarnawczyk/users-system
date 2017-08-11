$(document).ready(function() {
    $("#photoSelector").change(function(){
        var files = $("#photoSelector").prop("files");
        if (FileReader && files && files.length) {
            var fr = new FileReader();
            fr.onload = function () {
                $("#profilePhoto").attr("src", fr.result);
            }
            fr.readAsDataURL(files[0]);
        }
    });
});