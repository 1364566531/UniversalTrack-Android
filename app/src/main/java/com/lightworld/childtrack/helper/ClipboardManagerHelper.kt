package com.lightworld.childtrack.helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.lightworld.childtrack.RemarkUserBean
import com.lightworld.childtrack.TrackMapActivity
import com.lightworld.childtrack.lastQueryEntityName
import org.jetbrains.anko.startActivity

/**
 * Created by heyue on 2018/1/2.
 */
object ClipboardManagerHelper {
    /**
     * 检索剪切板 分割身份 进入跟踪页面
     * */
    fun discernSymbol(mContext: Context) {
        //获取 剪切板
        val cm = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copyText = cm.primaryClip?.getItemAt(0)?.text
        copyText?.let {
            if (it.contains('¥') && copyText.endsWith('¥')) {
                var split: List<String> = copyText?.split('¥')
                var id = split[1]
                //存入数据库
                HistoryQueryTable.saveData(RemarkUserBean(id))
                lastQueryEntityName = id

                //重置剪切板
                val mClipData = ClipData.newPlainText("", "")
                cm.primaryClip = mClipData
                mContext.startActivity<TrackMapActivity>()
            }
        }
    }

    /**
     * 分割身份ID
     * */
    fun splitSymbol(copyText: CharSequence?): String {
        copyText?.let {
            if (it.contains('¥') && copyText.endsWith('¥')) {
                var split: List<String> = copyText?.split('¥')
                return split[1]
            }
        }
        return copyText.toString()

    }
}