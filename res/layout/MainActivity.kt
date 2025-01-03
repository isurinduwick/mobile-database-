import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val addButton = findViewById<Button>(R.id.addButton)
        val displayButton = findViewById<Button>(R.id.displayButton)
        val displayText = findViewById<TextView>(R.id.displayText)

        addButton.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()

            if (name.isNotBlank() && age != null) {
                dbHelper.insertUser(name, age)
                nameInput.text.clear()
                ageInput.text.clear()
            }
        }

        displayButton.setOnClickListener {
            val users = dbHelper.getAllUsers()
            displayText.text = users.joinToString("\n")
        }
    }
}
