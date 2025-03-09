package jh.inspected

data class AutoInspectionResult(
    val status: String, // "GOOD" or "BAD"
    val tags: List<String>
)
