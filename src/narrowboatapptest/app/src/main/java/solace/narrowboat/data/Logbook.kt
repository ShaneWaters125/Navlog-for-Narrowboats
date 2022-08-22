package solace.narrowboat.data

data class Logbook(
        var tid: Int,
        var crew: String,
        var guests: String,
        var startLocation: String,
        var endLocation: String,
        var weather: String,
        var engineHours: String,
        var fuel: String,
        var water: String,
        var notes: String,
        var numLocks: String
)