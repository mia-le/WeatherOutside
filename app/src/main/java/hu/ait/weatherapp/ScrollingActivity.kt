package hu.ait.weatherapp

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import hu.ait.weatherapp.adapter.WeatherAdapter
import hu.ait.weatherapp.data.City
import hu.ait.weatherapp.touch.CityReyclerTouchCallback
import kotlinx.android.synthetic.main.activity_scrolling.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import java.util.*

class ScrollingActivity : AppCompatActivity(), CityDialog.CityHandler {


    lateinit var weatherAdapter: WeatherAdapter

    companion object{

        const val PREF_NAME = "PREF_ITEM"
        const val KEY_STARTED = "KEY_STARTED"
        const val KEY_LAST_USED = "KEY_LAST_USED"

        const val DIALOG = "Dialog"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            showAddTodoDialog()
        }

        var pushAnim = AnimationUtils.loadAnimation(this, R.anim.push_animation)

        btnDeleteAll.setOnClickListener{
            weatherAdapter.deleteAll()
            btnDeleteAll.startAnimation(pushAnim)
        }

        initRecyclerView()

        if (!wasStartedBefore()) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.fab)
                .setPrimaryText(getString(R.string.dialog_name))
                .setSecondaryText(getString(R.string.dialog_guide))
                .show()
        }

        saveStartInfo()
    }

    fun saveStartInfo() {
        var sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putBoolean(KEY_STARTED, true)
        editor.putString(KEY_LAST_USED, Date(System.currentTimeMillis()).toString())
        editor.apply()
    }

    fun wasStartedBefore() : Boolean {
        var sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        return sharedPref.getBoolean(KEY_STARTED, false)
    }

    private fun initRecyclerView() {

        var cityList = mutableListOf<City>()
        weatherAdapter = WeatherAdapter(this, cityList, this as ScrollingActivity)
        recyclerCity.adapter = weatherAdapter
        val touchCallbackList = CityReyclerTouchCallback(weatherAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallbackList)
        itemTouchHelper.attachToRecyclerView(recyclerCity)

        val itemDecorator = DividerItemDecoration(
            this, DividerItemDecoration.VERTICAL
        )
        recyclerCity.addItemDecoration(itemDecorator)

    }

    fun showAddTodoDialog(){
        CityDialog().show(supportFragmentManager, DIALOG)
    }



    fun saveTodo(city: City){
        weatherAdapter.addCity(city)
    }

    override fun cityCreated(city: City) {
        saveTodo(city)
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }

}