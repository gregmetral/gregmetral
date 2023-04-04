using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }


    public void loadAppleCatcher()
    {
        AsyncOperation asyncLoad = SceneManager.LoadSceneAsync("Title");
    }

    public void loadBrickBreaker()
    {
        AsyncOperation asyncLoad = SceneManager.LoadSceneAsync("BrickBreaker");
    }

    public void loadFurapiBird()
    {
        AsyncOperation asyncLoad = SceneManager.LoadSceneAsync("FurapiBird");
    }
}
