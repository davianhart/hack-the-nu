$.getJSON("https://api.myjson.com/bins/ikx2f", "", volCardCallback);

var volCardList = [];
var curIndex;

$(".read-more").mouseover(function () {
    $("#card-group-description-container").css("border-bottom-left-radius", "50px");

    $("#card-group-description-container").css("border-bottom-right-radius", "50px");
    $(".read-more").css("background-color", "#6395F2");

    $(".read-more").css("color", "white");

});

$(".read-more").mouseout(function () {
    $("#card-group-description-container").css("border-bottom-left-radius", "0");

    $("#card-group-description-container").css("border-bottom-right-radius", "0");
    $(".read-more").css("background-color", "white");

    $(".read-more").css("color", "#6395F2");

});

$(".read-more").click(function () {


    // We need to parse card data to show the user
    $("#info_text").text(curIndex.description);

});


function volCardCallback(data) {
    // Initialize volCardList Variable
    volCardList = data;
    removeChildren();
    curIndex = volCardList[0];
    createVolCard(volCardList[0]);

}

$(".nobtn, .yesbtn").click(function() {
    // Increment Index of list
    var x = [Math.floor(Math.random() * volCardList.length)];
    removeChildren();

    // Check array size
    if (volCardList.length <= 1) {
        $(".nobtn, .yesbtn").css("display", "none");
    }


    //Push Value of Card
    createVolCard(volCardList[x]);
    curIndex = volCardList[x];

    // Remove Values
    volCardList.splice(x, 1);

    // Remake Array
    var temp = [];
    for (var i = 0; i < volCardList.length; i++) {
        if (volCardList[i] != undefined) {
            temp.push(volCardList[i]);
        }
    }

    volCardList = temp;

});


function createVolCard(cardData) {

    // Card Varaibles
    var cardTitle, cardCompany, cardImage, cardTags, cardDescription;

    cardTitle = cardData.title;
    cardCompany = cardData.companyName;
    cardImage = cardData.imageLocation;
    cardTags = cardData.oppurtunityTags;
    cardDescription = cardData.description;

    cardTags.forEach(function (data) {
        $("#card-group-tags").append("<li class=\"card-group-tag-style\">"+data+"</li>")
    });

    $('#card-group-title').text(cardTitle);
    $('#card-group-company').text(cardCompany);
    $('#card-group-description').text(cardDescription);
    $("#card-group-img").attr("src", cardImage);

}

function removeChildren() {

    $("#card-group-tags").empty();
}