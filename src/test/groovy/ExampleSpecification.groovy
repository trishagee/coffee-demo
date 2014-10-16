import com.mongodb.MongoClient
import org.mongodb.morphia.Morphia
import spock.lang.Specification

class ExampleSpecification extends Specification {
    def 'should be able to unit test with Spock'() {
        given:
        int someIntValue;

        when:
        someIntValue = 1

        then:
        someIntValue == 1
    }

    def 'should be able to run integration tests'() {
        given:
        def databaseName = 'TheDatabaseName'
        def mongoClient = new MongoClient()
        def resource = new ExampleWebResource(mongoClient)
        
        when:
        resource.saveSomething(new SomePojo([id: 2, name: 'Chris']))

        then:
        // should be stored in a collection with the same name as the Class
        def collection = mongoClient.getDB(databaseName).getCollection('SomePojo')
        collection.count == 1

        then:
        // let's use Morphia to query for this inserted object
        def datastore = new Morphia().createDatastore(mongoClient, databaseName)
        SomePojo actualPojo = datastore.find(SomePojo, 'id', 2).get()
        actualPojo.id == 2
        actualPojo.name == 'Chris'

        cleanup:
        mongoClient.getDB(databaseName).dropDatabase()
        mongoClient.close()
    }


}
