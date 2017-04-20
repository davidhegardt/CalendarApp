var newDatum = new Date();
var year = newDatum.getFullYear();
var currentMonth;

function CALENDAR(){
var months = ["Januari", "Februari", "Mars", "April", "Maj", "Juni","Juli","Augusti","September","Oktober","November","December"];

    currentMonth = newDatum.getMonth();
    currentDay = newDatum.getDate();
    chosenMonth = newDatum.getMonth();
    //currentMonth = 3;

    document.getElementById("label").innerText = months[currentMonth] + " " + year;

    updateMonth();



    document.getElementById("prev").addEventListener(
           "click", function () {
               if (currentMonth == 0) {
                   currentMonth = 11;
               }
               else {
                   currentMonth -= 1;
               }
               if (currentMonth == 11) {
                   year -= 1;
                   next = false;
               }

               document.getElementById("label").innerText = months[currentMonth] + " " + year;
               updateMonth();

           }, false);

    document.getElementById("next").addEventListener(
           "click", function () {

               if (currentMonth == 11) {
                   currentMonth = 0;
               }
               else {
                   currentMonth += 1;
               }
               if (currentMonth == 0) {
                   year += 1;
                   next = true;
               }

               document.getElementById("label").innerText = months[currentMonth] + " " + year;
               //passValueToAndroid(months[currentMonth] + " " + year);
               updateMonth();


           }, false);

}

window.addEventListener("load", CALENDAR, false);

function passValueToAndroid(yourPassingValue) {
     Android.showMyValue(yourPassingValue);
 }

function updateMonth() {
    var maxDate;
    if (currentMonth == 1){
        maxDate = 29;
        document.getElementById(29).style.visibility = "hidden";
        document.getElementById(30).style.visibility = "hidden";
        document.getElementById(31).style.visibility = "hidden";
    }
    else if (currentMonth == 0 || currentMonth == 2 || currentMonth == 4 || currentMonth == 6 ||currentMonth == 7 ||currentMonth == 9 ||currentMonth == 11){
        maxDate = 32;
        document.getElementById(29).style.visibility = "visible";
        document.getElementById(30).style.visibility = "visible";
        document.getElementById(31).style.visibility = "visible";

    } else {
        maxDate = 31;
        document.getElementById(29).style.visibility = "visible";
        document.getElementById(30).style.visibility = "visible";
        document.getElementById(31).style.visibility = "hidden";
    }



    var value;
        for (var i = 1; i < maxDate; i++) {
            value = document.getElementById(i).innerText;
            document.getElementById(i).innerHTML = "<a href='TestActivity://" + value + "_" + currentMonth + "'>" + value + "</a>"

        }

}

