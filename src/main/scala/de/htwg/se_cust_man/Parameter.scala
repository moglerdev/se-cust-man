package de.htwg.se_cust_man


case class Parameter(
  name: String,
  argNames: List[String],
  description: String,
  required: Boolean,
  value: String
)

def parseParameters(args: List[String], parameters: List[Parameter]): List[Parameter] =
  return parameters.map(p => {
    val argName = p.argNames.find(arg => args.contains(arg))
    val value = argName match
      case Some(arg) => args(args.indexOf(arg) + 1)
      case None => ""
    p.copy(value = value)
  }).toList