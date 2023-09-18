# PDF2MD

pdf2md is a Node.js package for converting PDF documents to Markdown.

This program adds a GUI to pdf2md and lets users select individual files to convert. Normally pdf2md only supports batch converting the entire contents of a selected folder, which isn’t always ideal. Written in Java.   

![pdf2md](https://github.com/chr-hall/PDF2MD/assets/117752515/3d26bb15-5c64-4f77-a80d-6126a060247f)

### How to use:
1. Install the pdf2md Node.js package (see below for instructions).
2. Compile and run Main class.
3. Select PDF files to convert (it's possible to select multiple PDF files for conversion).
4. Select output folder.
5. Press **Convert** to begin.

*Note: this program has only been tested on Windows 11, no guarantees for other systems*

### Installing pdf2md:

The program expects you to have pdf2md installed already on your system. Instructions for installing pdf2md:

1. **Ensure Node.js and npm are Installed:**
   First, make sure you have Node.js and npm (Node Package Manager) installed on your computer. If not, download and install them from the official Node.js website: https://nodejs.org/

2. **Open Command Prompt:**
   Open the Command Prompt on your Windows computer. Press the Windows key, type "cmd," and hit Enter.

3. **Navigate to the Desired Directory:**
   If you want to install the package in a specific location, you can navigate there using the `cd` command. For instance, to install it on your desktop, type:
   
   ```
   cd C:\Users\<YourUsername>\Desktop
   ```

   Replace `<YourUsername>` with your actual Windows username.

4. **Install the Package:**
   To install the `@opendocsg/pdf2md` package, use the following command:

   ```
   npm install @opendocsg/pdf2md
   ```

   This command tells npm to download and install the package along with its necessary components.

5. **Wait for Installation:**
   npm will start the download and installation process. Just wait for it to finish. You'll see progress updates in the Command Prompt.


