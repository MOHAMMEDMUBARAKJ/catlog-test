#include <iostream>
#include <fstream>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

void processTestCase(const std::string& filename) {
    std::ifstream inputFile(filename);
    if (!inputFile.is_open()) {
        std::cerr << "Could not open the file: " << filename << std::endl;
        return;
    }

    json j;
    inputFile >> j;

    // Example: Accessing keys
    int n = j["keys"]["n"];
    int k = j["keys"]["k"];

    std::cout << "Test case from " << filename << ":\n";
    std::cout << "n: " << n << ", k: " << k << std::endl;

    // Process the rest of the JSON data as required...
}

int main() {
    processTestCase("testcase1.json");
    processTestCase("testcase2.json");
    return 0;
}
