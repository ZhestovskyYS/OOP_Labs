package data

data class Student (
    val firstname: String,
    val surname: String
)

val studentList =
    arrayListOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz"),
        Student("Rajesh", "Koothrappali"),
        Student("Amy", "Fowler")
    )