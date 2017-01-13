function calculate() {
    var difference = (param.distance * param.weight) * .653;
    var message = "Sorry no comment for that!";

    if (difference < 100) {
        message="You better start working!";
    }
    else
    if (difference > 101 && difference < 200) {
        message="Nice run, but you can do better.";
    }
    else
    if (difference > 201 && difference < 300) {
        message="Very good! Push above 300 next time.";
    }
    else
    if (difference > 301 && difference < 500) {
        message="Great! Your a runner.....keep it up!";
    }
    else
    if (difference > 501 && difference < 700) {
        message="Bill Rogers move over!";
    }
    else
    if (difference > 701) {
        message="Your my hero! Have a jelly doughnut.";
    }

    // Define calculated calories object message
    var result = {
        "burned_cal" : difference,
        "message" : message
    }
    return result;
}

calculate();