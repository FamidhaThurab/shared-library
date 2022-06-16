 def call(Map config = [:]) {
	sh "echo Hello ${config.name}."
	sh "echo Today is ${config.dayOfWeek}."
}
