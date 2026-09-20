package ir.seometer.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.seometer.app.data.HistoryStore
import ir.seometer.app.model.*
import ir.seometer.app.seo.SeoAnalyzer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AppState(val loading:Boolean=false,val progress:Int=0,val progressText:String="",val report:SeoReport?=null,val error:String?=null,val history:List<HistoryItem> = emptyList())
class AppViewModel(app:Application):AndroidViewModel(app){
    private val analyzer=SeoAnalyzer(); private val store=HistoryStore(app)
    private val _state=MutableStateFlow(AppState(history=store.load())); val state:StateFlow<AppState> = _state
    fun analyze(url:String){ if(url.isBlank())return; _state.value=_state.value.copy(loading=true,progress=1,error=null,report=null); viewModelScope.launch{ try{ val r=analyzer.analyze(url){p,t->_state.value=_state.value.copy(progress=p,progressText=t)}; store.add(HistoryItem(r.url,r.score,r.createdAt)); _state.value=_state.value.copy(loading=false,report=r,history=store.load()) }catch(e:Exception){_state.value=_state.value.copy(loading=false,error=e.message?:"خطای ناشناخته")}} }
    fun clearError(){_state.value=_state.value.copy(error=null)}
}
