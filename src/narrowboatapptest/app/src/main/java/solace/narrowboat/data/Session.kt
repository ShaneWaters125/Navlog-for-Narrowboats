package solace.narrowboat.data

data class Session(
        var sid: Int,
        var jid: Int,
        var startTime: String,
        var endTime: String,
        var date: String,
        var distance: String,
        var boatname: String
)