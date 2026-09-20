package ir.seometer.app.util

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import ir.seometer.app.model.SeoReport
import java.io.File
import java.io.FileOutputStream

object ReportExporter{
    fun create(context:Context,report:SeoReport):android.net.Uri{
        val dir=File(context.cacheDir,"reports").apply{mkdirs()}; val file=File(dir,"seo-report-${System.currentTimeMillis()}.pdf")
        val pdf=PdfDocument(); val page=pdf.startPage(PdfDocument.PageInfo.Builder(595,842,1).create()); val c=page.canvas
        val p=Paint().apply{isAntiAlias=true;textAlign=Paint.Align.RIGHT}
        p.textSize=24f;p.isFakeBoldText=true;c.drawText("سئومتر - گزارش سئو",550f,55f,p)
        p.textSize=14f;p.isFakeBoldText=false;c.drawText(report.url,550f,85f,p); c.drawText("امتیاز: ${report.score}/100",550f,110f,p)
        var y=150f; report.issues.filter{it.severity!=ir.seometer.app.model.Severity.PASSED}.take(18).forEach{p.textSize=13f;p.isFakeBoldText=true;c.drawText("• ${it.title}",550f,y,p); y+=18;p.textSize=10f;p.isFakeBoldText=false;c.drawText(it.detail.take(85),550f,y,p);y+=28}
        p.textSize=9f;c.drawText("طراح: امید قدسی زاده",550f,815f,p)
        pdf.finishPage(page); FileOutputStream(file).use{pdf.writeTo(it)}; pdf.close()
        return FileProvider.getUriForFile(context,"${context.packageName}.fileprovider",file)
    }
}
