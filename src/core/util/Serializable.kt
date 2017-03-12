/**
 * Interface is provided for specifying serialization in inherit classes
 *
 *  @author Alex Syrotenko (@flystyle)
 *  @since 0.1
 *  @version 0.1
 *  @see serialize
 */
interface Serializable {
   /**
    * @author Alex Syrotenko (@flystyle)
    *
    * Method serializes inherit class data.
    *
    * @since 0.1
    * @version 0.1
    * @see Serializable#serialize()
    * @return String after some specific serialization in inherit class
    */
    fun serialize () : String
}