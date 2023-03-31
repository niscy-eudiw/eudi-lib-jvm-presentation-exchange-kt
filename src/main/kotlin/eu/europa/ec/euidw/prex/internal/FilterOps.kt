package eu.europa.ec.euidw.prex.internal

import eu.europa.ec.euidw.prex.Filter
import eu.europa.ec.euidw.prex.JsonString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.pwall.json.schema.JSONSchema


internal class FilterOps(private val filterSerializer: (Filter)->String) {

    /**
     * Checks whether a given [json][JsonString] satisfies the
     * constraints described in the [Filter]
     */
    internal fun Filter.isMatchedBy(j: JsonString): Boolean = isValid(this, j)

    private fun isValid(f: Filter, j: JsonString): Boolean {
        val jsonStr = filterSerializer(f)
        val jsonSchema =  JSONSchema.parse(jsonStr)
        return jsonSchema.validate(j.value, net.pwall.json.pointer.JSONPointer.root)
    }

}