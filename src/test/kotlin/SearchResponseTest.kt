import src.main.kotlin.SearchResponse
import src.main.kotlin.SearchResponseDeserializer
import kotlin.test.Test


internal class SearchResponseTest{

    @Test
    fun testObject(){

        //language=JSON
        val input = """
            {
                "title": "Search",
                "description": "Search for a specific item",
                "en_US": {
                    "title": "innerTitle"
                }
                
            }
        """.trimIndent()

        val gson = com.google.gson.Gson()
        val simpleSearchItem = gson.fromJson(input, SearchResponse::class.java)

        val complex = gson.newBuilder().registerTypeAdapter(SearchResponse::class.java, SearchResponseDeserializer()).create()
        val complexLocalized = complex.fromJson(input, SearchResponse::class.java)

        simpleSearchItem.localized = complexLocalized.localized

        assert(complexLocalized?.localized?.title == "innerTitle")

    }
}
