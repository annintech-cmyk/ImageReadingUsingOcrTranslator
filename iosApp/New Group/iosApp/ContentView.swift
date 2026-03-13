import SwiftUI
//import Shared


import SwiftUI
import PhotosUI

struct ContentView: View {
   
    var body: some View {
        if #available(iOS 18.0, *) {
            TabView {
                Tab(Constants.home, systemImage: "camera") {
                    Text(Constants.home)
                    
                }
                Tab("Camera", systemImage: "camera") {
                    Text("Camera")
                }
            }
           
        } else {
            // Fallback on earlier versions
            let ststsuc = "Ann"
            var name: String? = nil
            let final = name ?? ststsuc
            let final2 = name == nil ? ststsuc : "Anonymous"
        }
    }
}
