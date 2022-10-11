package solace.narrowboat.data

data class Boat(
    var bid: Int,
    var name: String,
    var owner: String,
    var registeredNumber: String,
    var cin: String,
    var boatType: String,
    var boatModel: String,
    var length: String,
    var beam: String,
    var draft: String
)