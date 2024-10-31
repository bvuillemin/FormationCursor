# Cursor AI Demo for Python and Java

## Principles for Crafting Effective Prompts

- **Be Specific**: Clearly define what you want the AI to do. Include method names, classes, or functions for precise targeting.
- **Provide Context**: Help the AI understand the broader context (e.g., a legacy project, a particular use case) to generate relevant outputs.
- **Define the Output Structure**: Specify how you want the results presented (e.g., step-by-step explanations, code comments, test case details).
- **Emphasize Clarity and Usefulness**: Especially in documentation or explanations, ask for clear, concise language suited for developers or end-users.

## Python Demo Prompts

1. **TAB**
   - Complete the "to be completed" sections (click just before this text and press the “tab” button)
   - Double enter -> start writing “# Var”
   - Auto-complete in text and in return statements

2. **CTRL K**
   - Follow the notebook instructions

3. **CTRL L**
   - **Basic Prompt (with errors)**: Create a Python script that sets up a database to store car details, populates it with sample data, and displays it on the terminal.
   - **Complete Prompt**: As a Python developer, create a file for a script to set up an SQLite database to store car information. The script should include a table with these columns: maker, model, year, color, and price. The script should:
     - Create the database and the corresponding table if they don't already exist.
     - Insert sample data into the table when it's empty, with consistent information.
     - Display the table data in the terminal in a readable format (with well-aligned columns).
     - Provide an option to filter cars based on criteria like year or maker.
     - Document each function for better code readability.
     - Use Python’s standard libraries, ensuring the code is optimized for easy modification.

   - **Prompt 2**: Great. Now edit the code in place to add a simple GUI using Tkinter that shows the car details.

## Java Demo Prompts

1. **Launch the Java Project and Build Before the Demo**
   - Key Scripts:
     - `mvn spring-boot:run`
     - `mvn spring-javaformat:apply`

2. **Project Interpretation and Explanation**
   - **Prompt**: Analyze and provide a high-level explanation of the project, detailing both functional and technical aspects. Identify the core functionalities and list the technologies in use. @Codebase

3. **Interpretation and Explanation of Existing Legacy Code**
   - **Prompt**: Next, interpret and explain the validate method within the PetValidator class, explaining its purpose, logic, and flow. Tailor the explanation for developers who are new to the codebase or working with long, undocumented methods. @PetValidator.java

4. **Generating Mermaid Code for Draw.io**
   - **Prompt**: Please generate the database schema of this project using Mermaid code. @Codebase
   - **Prompt 2**: From the Mermaid code, can you generate the class diagram associated with the JPA entity? @Codebase

5. **Documentation**
   - **Prompt**: Generate complete documentation for the PetController class. Include details for each method, parameters, return types, and key usage scenarios. This documentation should be structured clearly to serve as a helpful reference for developers looking to understand or work with this class. @PetController.java
   - **Prompt 2**: CTRL + K: \document annotations and javadoc

6. **Unit Tests**
   - **Prompt**: Create a file for unit tests for the validate method in the PetValidator class. Design these tests to cover different scenarios to ensure robust validation across edge cases. Also, remove any redundant test classes, particularly ValidatorTests, to maintain streamlined and relevant tests. @Codebase

7. **Integration (or API) Tests**
   - **Prompt**: Create a file for an integration test for the OwnerController class, verifying its function in a broader application context. The test cases should validate that the integration behaves as expected in real-world scenarios. @Codebase
   - **If using @Transactional**: Why did you write the test with "@Transactional"?

8. **Bug Fixing**
   - **Prompt**: I can create a pet that has a birth date after today. How to ensure this won’t happen? @Codebase

9. **Refactoring**
   - **Prompt**: Refactor the PetController class, aiming to simplify the code while preserving all functionalities. Improve readability by removing redundancies and optimizing logic where possible. Ensure the refactored code is easier to understand and maintain, especially in a legacy project context. 