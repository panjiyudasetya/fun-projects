function calculate() {
    // Define calculated calories object message
    var result = {
        "burned_cal"    : "x103",
        "message"       : "Test ub-normal attributes",
        "child"         : {
            "id"    : "1234",
            "name"  : "Level 1 Child Object",
            "child" : {
                "id"    : "rcc_1234",
                "name"  : "Level 2 Child Object"
            }
        }
    }
    return result;
}

calculate();