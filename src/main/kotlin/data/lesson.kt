package data

data class Lesson(
    val subject: String,
    val students: ArrayList<Student>
)

val s_c = Student("Sheldon", "Cooper")
val l_h = Student("Leonard", "Hofstadter")
val h_w = Student("Howard", "Wolowitz")
val r_k = Student("Rajesh", "Koothrappali")
val a_f = Student("Amy", "Fowler")

val lessonsList =
   /* arrayListOf(*/
        Lesson("Math", arrayListOf(s_c, h_w, a_f))/*,
        Lesson("Philosophy", arrayListOf(l_h, h_w, r_k)),
        Lesson("Physics", arrayListOf(s_c, l_h, h_w))
    )
*/