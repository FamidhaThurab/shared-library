 def call(String name, String dayOfWeek) {
	sh "echo Hello ${name}."
	sh "echo Today is ${dayOfWeek}."
}
