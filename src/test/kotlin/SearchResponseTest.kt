import src.main.kotlin.SearchResponse
import src.main.kotlin.SearchResponseDeserializer
import src.main.kotlin.Wrapper
import kotlin.test.Test


internal class SearchResponseTest{

    @Test
    fun testObject(){

        //language=JSON
        val input = """
            {
                "title": "Search",
                "description": "Search for a specific item",
                "wraps": [
                    {
                      "name": "wrapped 1",
                      "en_US": {
                        "title": "wrapped title 1"
                      }
                    },
                    {
                      "name": "wrapped 2",
                      "en_US": {
                        "title": "wrapped title 2"
                      }
                    },
                    {
                      "name": "wrapped 3",
                      "de_DE": {
                        "title": "wrapped title 3"
                      }
                    }
                ]
            }
        """.trimIndent()

        val gson = com.google.gson.Gson()

        val complex = gson.newBuilder().registerTypeAdapter(Wrapper::class.java, SearchResponseDeserializer()).create()
        val complexLocalized = complex.fromJson(input, SearchResponse::class.java)

        assert(complexLocalized != null)
        assert(complexLocalized.wraps?.size == 3)
        assert(complexLocalized.wraps?.get(0)?.name == "wrapped 1")
        assert(complexLocalized.wraps?.get(1)?.name == "wrapped 2")
        assert(complexLocalized.wraps?.get(2)?.name == "wrapped 3")
        assert(complexLocalized.wraps?.get(0)?.localized?.title == "wrapped title 1")
        assert(complexLocalized.wraps?.get(1)?.localized?.title == "wrapped title 2")
        assert(complexLocalized.wraps?.get(2)?.localized?.title == "wrapped title 3")


    }
}
