The Plan
   1. Make PlantParser.py iterate through each line in the 
csv making a new array for each plant a new object for 
each of the plants relevant properties
      1. Common name (which will become the key)
      2. Scientific name
      3. herb. or woody
      4. leps
      6. spread
      7. required light (0-10, 0 is <=10 lux, 10 is >= 100,000 lux)
      8. soil texture (0-10, 0 is clay, 10 is sand)
      9. moisture (0-10, 0 is <=10%, 10 is >= 90%)
      10. average height in cm
      11. growth data (more data)    
      12. specification data (more specific data)
      
### csv column format
Family as listed by Bonap,genera,common name,
herbaceous or woody,Native Delaware Leps

in order to correctly use the api, we need 
the scientific name, but the excel sheet
only gives us the common name and genus

### solution:
use scrapy with google to find the scientific name in the 
common info section

check the first common name, if the genus matches apply the 
API

if it doesn't match, don't add that line

### new approach
use treffle's api to get the scientific names in a genus with
complete images then load their data.

### ad hoc approach (complete)
hard code 20 scientific names into a csv for now
and get the json file for them. Then put them into the hashmap