using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class paddle : MonoBehaviour
{
    [SerializeField] protected float SPEED = 6;
    [SerializeField] protected AudioSource pointWinSound;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKey(KeyCode.RightArrow))
        {
            if (transform.position.x < 4.7)
            {
                transform.Translate(SPEED * Time.deltaTime, 0, 0);
            }

        }
        if (Input.GetKey(KeyCode.LeftArrow))
        {
            if (transform.position.x > -4.7)
            {
                transform.Translate(-SPEED * Time.deltaTime, 0, 0);
            }
        }
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            AsyncOperation asyncLoad = SceneManager.LoadSceneAsync("MainMenu");
        }
    }
    public void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.tag == "jesustag")
        {
            pointWinSound.Play();

        }
        

    }
}
