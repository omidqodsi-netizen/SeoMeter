package ir.seometer.app.model

enum class Severity { CRITICAL, WARNING, PASSED }

data class SeoIssue(val title:String,val detail:String,val fix:String,val severity:Severity)

data class SeoMetrics(
    val pages:Int=0,val links:Int=0,val images:Int=0,val missingAlt:Int=0,
    val responseMs:Long=0,val mobileScore:Int=100,val metaScore:Int=100,val linkScore:Int=100
)

data class SeoReport(
    val url:String,val score:Int,val critical:Int,val warnings:Int,val passed:Int,
    val issues:List<SeoIssue>,val metrics:SeoMetrics,val createdAt:Long=System.currentTimeMillis()
)

data class HistoryItem(val url:String,val score:Int,val createdAt:Long)
