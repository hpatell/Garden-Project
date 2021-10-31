import requests
import json
from csv import DictReader


def printAsFormattedJSON(jsonObject):
    """Prints JSON as a multi-line, indented string rather than a jumbled mess.
       Truncates string that are more than 1,000 characters."""
    print(json.dumps(jsonObject, indent=2)[0:1000])


key = "xjVtmCxrga2w4BjI-V4TKrWCU0_36uTWXidI2KecaNk"

data = []
count = 0
with open('hardcoded_scientific_names.csv', 'r', encoding="utf-8-sig") as read_obj:
    csv_dict_reader = DictReader(read_obj)
    for row in csv_dict_reader:
        # print(row)
        # print(row['scientific name'])
        sci_name = row['scientific name']
        # TODO: now feed into api
        r = requests.get("https://trefle.io/api/v1/species/" + sci_name + "?token=" + key)
        plant = r.json()
        try:
            plant = r.json()
            # printAsFormattedJSON(plant)
            image_link = plant["data"]["image_url"]
            if image_link:
                # checking if image is actually there (not null)
                response = requests.get(image_link, verify=False)

                file = open("./plant_images/" + plant["data"]["scientific_name"] + ".jpeg", "wb")
                file.write(response.content)
                file.close()
                data.append(
                    {
                        "common name": plant["data"]["common_name"],
                        "scientific name": plant["data"]["scientific_name"],
                        "h or w": row['herb or woody'],
                        "leps": row['Native Delaware Leps'],
                        # No image file name, the file name is just the scientific name of the plant
                        # "image file name": image_file_name
                        "spread": plant["data"]["growth"]["spread"]["cm"],
                        # Currently taking lots of plants without a spread, light,
                        # soil, moisture, or height data
                        # When making better data set (not hard coded, only take plants
                        # with all the data
                        "light": plant["data"]["growth"]["light"],
                        "soil": plant["data"]["growth"]["soil_texture"],
                        "moisture": plant["data"]["growth"]["atmospheric_humidity"],
                        # average height
                        "height": plant["data"]["specifications"]["average_height"]["cm"],
                        "growth_data": plant["data"]["growth"],
                        "spec_data": plant["data"]["specifications"]
                    }
                )
                count = count + 1
                print(data)
        except KeyError:
            print("Not enough plant data")

json_string = json.dumps(data)
print(json_string)
print("Number of plants in database")
print(count)

with open('plant_database.json', 'w') as outfile:
    json.dump(data, outfile)