package com.example.myapplication.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSqliteBinding
import com.example.myapplication.databinding.PatientItemLayoutBinding


class SqliteFragment : BaseFragment<FragmentSqliteBinding>() {
    private lateinit var sqliteRepository: SqliteRepository
    private var patientAdapter = PatientAdapter()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSqliteBinding = FragmentSqliteBinding.inflate(inflater, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mySqliteOpenHelper = MySqliteHelper(requireContext())
        sqliteRepository = SqliteRepository(mySqliteOpenHelper.writableDatabase)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.savePatientBtn.setOnClickListener {
            val name = binding.nameEdt.text.toString()
            val city = binding.cityEdt.text.toString()
            if (name.isEmpty()) {
                binding.nameEdt.error = "Patient name cannot be empty."
                binding.nameEdt.requestFocus()
            } else if (city.isEmpty()) {
                binding.cityEdt.error = "Patient city cannot be empty."
                binding.cityEdt.requestFocus()
            } else {
                val patient = Patient(name = name, city = city)
                val patientId =sqliteRepository.insertPatient(patient)
                patient.id = patientId
                // notify UI
                patientAdapter.addPatientItem(patient)
            }
        }
        binding.getAllPatientInformationBtn.setOnClickListener {
            binding.allPatientInformationRecyclerView.adapter = patientAdapter
            val allPatients = sqliteRepository.getAllPatientInformation()
            patientAdapter.addAllPatients(allPatients)
        }
    }



}

class MySqliteHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "mydatabase.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("ddddd", "onCreate is called")
        db.execSQL(
            "create table Patient(id integer primary key autoincrement," +
                    "name text, phoneNumber text, city text );"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("ddddd", "onUpgrade is called oldVersion = $oldVersion , newVersion = $newVersion")

    }
}

//All of these logic happens on Main Thread .
class SqliteRepository(private val sqLiteDatabase: SQLiteDatabase) {
    init {
        if (sqLiteDatabase.isReadOnly) {
            throw Exception("It should be writable")
        }
    }

    fun insertPatient(patient: Patient):Long {
        val contentValues = ContentValues()
        contentValues.put("name", patient.name)
        contentValues.put("city", patient.city)
        return sqLiteDatabase.insert("Patient", null, contentValues)
    }

    fun getAllPatientInformation(): List<Patient> {
        val patients = mutableListOf<Patient>()
        val cursor = sqLiteDatabase.query(
            "Patient",
            null, // Passing null will return all columns
            null,
            null,
            null,
            null,
            null,
            null
        )
        try{
            var hasNext = cursor.moveToFirst()
            while (hasNext){
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val city = cursor.getString(cursor.getColumnIndexOrThrow("city"))
                patients.add(Patient(id = id.toLong(), name = name, city = city))
                hasNext = cursor.moveToNext()
            }
        }catch (illegalArgumentException : IllegalArgumentException){
            Log.d("ddddd" , illegalArgumentException.message.toString())
        }finally {
            cursor.close()
        }
        return patients
    }
}

data class Patient(var id: Long? = null, val name: String, val city: String)

class PatientAdapter : RecyclerView.Adapter<PatientViewHolder>() {
    private val patients: MutableList<Patient> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = PatientItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.view.apply {
            patientIdTxt.text = patients[position].id?.toString()
            patientNameTxt.text = patients[position].name
            patientCityTxt.text = patients[position].city
        }

    }

    fun addAllPatients(patients : List<Patient>){
        this.patients.clear()
        this.patients.addAll(patients)
        notifyItemRangeInserted(0,patients.size)
    }

    fun addPatientItem(patientModel : Patient){
        patients.add(patientModel)
        notifyItemInserted(patients.size - 1)
//        notifyItemRangeChanged(names.size - 1 , names.size)
    }

    override fun getItemCount(): Int {
        return patients.size
    }
}

class PatientViewHolder(val view: PatientItemLayoutBinding) : RecyclerView.ViewHolder(view.root)