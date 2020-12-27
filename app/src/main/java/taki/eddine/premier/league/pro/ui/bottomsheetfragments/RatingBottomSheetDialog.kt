package taki.eddine.premier.league.pro.ui.bottomsheetfragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hsalf.smileyrating.SmileyRating
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import taki.eddine.premier.league.pro.databinding.RatinglayoutBinding
import timber.log.Timber

class RatingBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding : RatinglayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RatinglayoutBinding.inflate(layoutInflater)


            binding.smileRating.setSmileySelectedListener {
                CoroutineScope(Dispatchers.Main).launch {
                if(SmileyRating.Type.TERRIBLE == it || SmileyRating.Type.BAD == it) {
                    Toast.makeText(requireContext(), "Sorry To hear That :(", Toast.LENGTH_SHORT).show()
                    delay(2000)
                }else if( SmileyRating.Type.OKAY == it || SmileyRating.Type.GOOD == it || SmileyRating.Type.GREAT == it){
                    Toast.makeText(requireContext(),"Glad that you like the app", Toast.LENGTH_SHORT).show()
                    delay(2000)
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + "taki.eddine.premier.league.pro")))
                    }catch (e : Exception){
                        Timber.d("Exception ${e.message}")
                    }
                    dismiss()
                }
                binding.smileRating.setTitle(SmileyRating.Type.GREAT, "Awesome");
                binding.smileRating.setFaceColor(SmileyRating.Type.GREAT, Color.BLUE);
                binding.smileRating.setFaceBackgroundColor(SmileyRating.Type.GREAT, Color.RED);
            }
        }
        return binding.root
    }
}