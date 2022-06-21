package solace.messageapp.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Item Decoration to set a recycle viewers spacing
 *
 * @author Shane Waters
 * @version 1.0 (24/04/2021)
 */
class TopSpacingItemDecoration(private val padding: Int): RecyclerView.ItemDecoration(){
    /**
     * Sets recycle viewer padding
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = padding
    }
}